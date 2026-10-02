const databaseName = process.env.MONGO_INITDB_DATABASE;
const webshopPassword = process.env.WEBSHOP_PASSWORD;

db.createUser({
    user: "webshop_user",
    pwd: webshopPassword,
    roles: [
        {
            role: "readWrite",
            db: databaseName
        }
    ]
});

db.createCollection("T_Items");
db.createCollection("T_Orders");
db.createCollection("T_Users");

db.T_Items.insertMany([
    {
        name: "Apple",
        description: "Round and red",
        category: "Food",
        price: 6,
        stock: 10
    },
    {
        name: "Pear",
        description: "Oval and green",
        category: "Food",
        price: 5,
        stock: 25
    }
]);

db.T_Users.insertMany([
    {
        username: "hans",
        passwordHash: "$2a$12$33oqesNra/E.ZFCMPG5yjuNOunS4LM1NJl5ThmtWY2MWOXQXnH9Ga<",
        role: "ADMIN"
    },
    {
        username: "helena",
        passwordHash: "temporary",
        role: "STAFF"
    },
    {
        username: "anna",
        passwordHash: "temporary",
        role: "CUSTOMER"
    }
]);
