def get_blocked_ips():
    result = subprocess.run('netsh advfirewall firewall show rule name=all', shell=True, capture_output=True, text=True)
    lines = result.stdout.splitlines()
    blocked_ips = []

    current_rule = {}
    for line in lines:
        if line.startswith("Rule Name:"):
            if "Block " in line:
                current_rule = {"Rule Name": line.split(":", 1)[1].strip()}
        elif "Remote IP" in line and current_rule:
            ip = line.split(":", 1)[1].strip()
            current_rule["RemoteIP"] = ip
            blocked_ips.append(f'{current_rule["Rule Name"]} -> {ip}')
            current_rule = {}

    return "\n".join(blocked_ips) if blocked_ips else "No blocked IPs found."
