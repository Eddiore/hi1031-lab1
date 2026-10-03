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

db.T_Items.createIndex(
    { name: 1, category: 1 },
    { unique: true }
);

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

db.T_Users.createIndex(
    { username: 1 },
    { unique: true }
);

db.T_Users.insertMany([
    {
        username: "hans",
        passwordHash: "$2a$12$PEbbSceXMavk23T8Q6E1hOVMc9NeZj467KEACEpnFwo0o7RdRq6su",
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
