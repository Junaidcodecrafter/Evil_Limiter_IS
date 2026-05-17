from auth import login_system
from crypto_module import CryptoModule
from network_scanner import scan_network
from limiter import block_ip, unblock_ip

def main():
    if not login_system():
        return

    crypto = CryptoModule()

    while True:
        print("""
        === Evil Limiter Menu ===
        1. Scan Network
        2. Block Suspicious IP
        3. Unblock IP
        4. Encrypt a Message
        5. Decrypt a Message
        6. Exit
        """)
        choice = input("Enter your choice: ")

        if choice == "1":
            ip_prefix = input("Enter your IP Prefix (e.g., 192.168.1): ")
            scan_network(ip_prefix)

        elif choice == "2":
            ip_to_block = input("Enter IP to block: ")
            block_ip(ip_to_block)

        elif choice == "3":
            ip_to_unblock = input("Enter IP to unblock: ")
            unblock_ip(ip_to_unblock)

        elif choice == "4":
            msg = input("Enter message to encrypt: ")
            encrypted = crypto.encrypt(msg)
            print(f"Encrypted: {encrypted}")

        elif choice == "5":
            encrypted_msg = input("Enter encrypted token: ").encode()
            decrypted = crypto.decrypt(encrypted_msg)
            print(f"Decrypted: {decrypted}")

        elif choice == "6":
            print("Exiting Evil Limiter.")
            break

        else:
            print("Invalid choice!")

if __name__ == "__main__":
    main()
