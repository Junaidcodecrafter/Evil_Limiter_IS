import os

def scan_network(ip_prefix):
    print("[*] Scanning devices...")
    for i in range(1, 255):
        ip = f"{ip_prefix}.{i}"
        response = os.system(f"ping -n 1 -w 100 {ip} > nul")
        if response == 0:
            print(f"[+] Active Device Found: {ip}")
