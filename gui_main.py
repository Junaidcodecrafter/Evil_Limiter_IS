import tkinter as tk
from tkinter import simpledialog, messagebox, scrolledtext
import subprocess
import os
from cryptography.fernet import Fernet

# --- Authentication Module ---
def login_system(username, password):
    return username == "admin" and password == "evil123"

# --- Crypto Module ---
class CryptoModule:
    def __init__(self):
        self.key = Fernet.generate_key()
        self.cipher = Fernet(self.key)

    def encrypt(self, message: str) -> bytes:
        return self.cipher.encrypt(message.encode())

    def decrypt(self, token: bytes) -> str:
        return self.cipher.decrypt(token).decode()

# --- Network Scanner ---
def scan_network(ip_prefix):
    result = ""
    for i in range(1, 255):
        ip = f"{ip_prefix}.{i}"
        response = os.system(f"ping -n 1 -w 100 {ip} > nul")
        if response == 0:
            result += f"[+] Active Device Found: {ip}\n"
    return result if result else "No active devices found."

# --- Firewall Control ---
def block_ip(ip_address):
    subprocess.call(
        f'netsh advfirewall firewall add rule name="Block {ip_address}" dir=in action=block remoteip={ip_address}',
        shell=True
    )

def unblock_ip(ip_address):
    subprocess.call(
        f'netsh advfirewall firewall delete rule name="Block {ip_address}"',
        shell=True
    )

def get_blocked_ips():
    result = subprocess.run('netsh advfirewall firewall show rule name=all', shell=True, capture_output=True, text=True)
    lines = result.stdout.splitlines()
    blocked = []

    current_rule_name = None
    for line in lines:
        if line.startswith("Rule Name:"):
            current_rule_name = line.split(":", 1)[1].strip()
        elif "Remote IP" in line and current_rule_name and current_rule_name.startswith("Block "):
            ip = line.split(":", 1)[1].strip()
            blocked.append((current_rule_name, ip))
            current_rule_name = None  # Reset for next rule

    return blocked

# --- GUI App ---
class EvilLimiterApp:
    def __init__(self, root):
        self.root = root
        self.crypto = CryptoModule()
        self.root.geometry("400x400")
        self.root.configure(bg="#1e1e2f")
        self.root.title("Evil Limiter Login")
        self.build_login()

    def style_button(self, text, command):
        return tk.Button(self.root, text=text, width=30, bg="#4e4e9a", fg="white",
                         font=("Helvetica", 10, "bold"), command=command, relief="flat", bd=0, padx=10, pady=5)

    def build_login(self):
        self.clear_window()
        tk.Label(self.root, text="Evil Limiter", bg="#1e1e2f", fg="#f05454",
                 font=("Helvetica", 18, "bold")).pack(pady=20)

        tk.Label(self.root, text="Username:", bg="#1e1e2f", fg="white").pack()
        self.username_entry = tk.Entry(self.root, font=("Helvetica", 10))
        self.username_entry.pack(pady=5)

        tk.Label(self.root, text="Password:", bg="#1e1e2f", fg="white").pack()
        self.password_entry = tk.Entry(self.root, show="e", font=("Helvetica", 10))
        ##self.password_entry = tk.Entry(self.root, show= "*", font=("Helvetica",10))
        self.password_entry.pack(pady=5)

        self.style_button("Login", self.verify_login).pack(pady=20)

    def verify_login(self):
        user = self.username_entry.get().strip()
        pwd = self.password_entry.get().strip()
        if login_system(user, pwd):
            self.build_dashboard()
        else:
            messagebox.showerror("Login Failed", "Invalid credentials!")

    def build_dashboard(self):
        self.clear_window()
        self.root.title("Evil Limiter Dashboard")

        tk.Label(self.root, text="Dashboard", bg="#1e1e2f", fg="#f05454",
                 font=("Helvetica", 16, "bold")).pack(pady=10)

        self.style_button("Scan Network", self.gui_scan_network).pack(pady=5)
        self.style_button("Block IP", self.gui_block_ip).pack(pady=5)
        self.style_button("Unblock IP", self.gui_unblock_ip).pack(pady=5)
        self.style_button("View Blocked IPs", self.gui_view_blocked_ips).pack(pady=5)
        self.style_button("Encrypt Message", self.gui_encrypt).pack(pady=5)
        self.style_button("Decrypt Message", self.gui_decrypt).pack(pady=5)
        self.style_button("Exit", self.root.quit).pack(pady=5)

    def gui_scan_network(self):
        prefix = simpledialog.askstring("IP Prefix", "Enter your IP prefix (e.g., 192.168.1):")
        if prefix:
            result = scan_network(prefix)
            self.show_output("Network Scan Result", result)

    def gui_block_ip(self):
        ip = simpledialog.askstring("Block IP", "Enter IP to block:")
        if ip:
            block_ip(ip)
            messagebox.showinfo("IP Blocked", f"Blocked IP: {ip}")

    def gui_unblock_ip(self):
        ip = simpledialog.askstring("Unblock IP", "Enter IP to unblock:")
        if ip:
            unblock_ip(ip)
            messagebox.showinfo("IP Unblocked", f"Unblocked IP: {ip}")

    def gui_view_blocked_ips(self):
        blocked_list = get_blocked_ips()

        win = tk.Toplevel(self.root)
        win.title("Blocked IPs")
        win.configure(bg="#2d2d44")

        if not blocked_list:
            tk.Label(win, text="No blocked IPs found.", bg="#2d2d44", fg="white", font=("Helvetica", 12)).pack(pady=20)
            return

        tk.Label(win, text="Blocked IPs", bg="#2d2d44", fg="#f05454", font=("Helvetica", 14, "bold")).pack(pady=10)

        frame = tk.Frame(win, bg="#2d2d44")
        frame.pack(padx=10, pady=10)

        for rule_name, ip in blocked_list:
            row = tk.Frame(frame, bg="#2d2d44")
            row.pack(fill="x", pady=3)

            lbl = tk.Label(row, text=f"{ip}", bg="#2d2d44", fg="white", width=25, anchor="w", font=("Courier", 10))
            lbl.pack(side="left", padx=5)

            btn = tk.Button(row, text="Unblock", bg="#f05454", fg="white", font=("Helvetica", 9, "bold"),
                            command=lambda ip=ip, row=row: self.unblock_from_list(ip, row))
            btn.pack(side="right", padx=5)

    def unblock_from_list(self, ip, row_widget):
        unblock_ip(ip)
        row_widget.destroy()
        messagebox.showinfo("IP Unblocked", f"{ip} has been unblocked.")

    def gui_encrypt(self):
        msg = simpledialog.askstring("Encrypt Message", "Enter message to encrypt:")
        if msg:
            encrypted = self.crypto.encrypt(msg)
            self.show_output("Encrypted Message", encrypted.decode())

    def gui_decrypt(self):
        token = simpledialog.askstring("Decrypt Message", "Enter encrypted token:")
        if token:
            try:
                decrypted = self.crypto.decrypt(token.encode())
                self.show_output("Decrypted Message", decrypted)
            except Exception:
                messagebox.showerror("Error", "Invalid token or decryption failed!")

    def show_output(self, title, content):
        output_window = tk.Toplevel(self.root)
        output_window.title(title)
        output_window.configure(bg="#2d2d44")

        txt = scrolledtext.ScrolledText(output_window, wrap=tk.WORD, width=70, height=20,
                                        bg="#1e1e2f", fg="white", insertbackground="white",
                                        font=("Courier", 10))
        txt.pack(padx=10, pady=10)
        txt.insert(tk.END, content)
        txt.config(state=tk.DISABLED)

    def clear_window(self):
        for widget in self.root.winfo_children():
            widget.destroy()

# --- Main ---
if __name__ == '__main__':
    root = tk.Tk()
    app = EvilLimiterApp(root)
    root.mainloop()
