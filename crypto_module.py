from cryptography.fernet import Fernet

class CryptoModule:
    def __init__(self):
        self.key = Fernet.generate_key()
        self.cipher = Fernet(self.key)

    def encrypt(self, message: str) -> bytes:
        return self.cipher.encrypt(message.encode())

    def decrypt(self, token: bytes) -> str:
        return self.cipher.decrypt(token).decode()

    def get_key(self):
        return self.key
