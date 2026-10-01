use Webshop;

db.createUser({
   user: "webapp_user",
    pwd: "Abcde123#",
    roles: [
        {
            role: "readWrite",
            db: "Webshop"
        }
    ]
});

db.createCollection("T_Users");
db.createCollection("T_Items");