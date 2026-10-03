# HI1031 - Lab1
## Setup
1. Clone the repository: `git clone https://github.com/Eddiore/hi1031-lab1.git`
2. Open the project in `IntelliJ`.
3. Select: `View -> Tool Windows -> Maven`
4. Expand the `Lifecycle` folder.
5. Double-click `package`.
6. Create a `.env` file under the project root and add the following lines to the file (replacing \<placeholder\> with real passwords):
```text
MONGO_PASSWORD=<placeholder>
MYSQL_PASSWORD=<placeholder>
WEBSHOP_PASSWORD=<placeholder>
```
7. Open a terminal in project root and run: `docker compose up -d`
8. Open a web browser and go to `http://localhost:27017/lab1`

## Credentials
- **username:** hans
- **password:** RandomPassword
