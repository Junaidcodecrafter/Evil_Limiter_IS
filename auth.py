def login_system():
    username = "admin"
    password = "evil123"
    
    print("=== Evil Limiter Login ===")
    try:
        user = input("Username: ").strip()
        pwd = input("Password: ").strip()
    except EOFError:
        print("[-] Error reading input!")
        return False

    if user == username and pwd == password:
        print("[+] Login successful!")
        return True
    else:
        print("[-] Invalid credentials!")
        return False
