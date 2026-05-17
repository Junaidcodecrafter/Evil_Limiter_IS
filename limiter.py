import subprocess

def block_ip(ip_address):
    print(f"[*] Blocking IP: {ip_address}")
    subprocess.call(
        f'netsh advfirewall firewall add rule name="Block {ip_address}" dir=in action=block remoteip={ip_address}',
        shell=True
    )

def unblock_ip(ip_address):
    print(f"[*] Unblocking IP: {ip_address}")
    subprocess.call(
        f'netsh advfirewall firewall delete rule name="Block {ip_address}"',
        shell=True
    )
