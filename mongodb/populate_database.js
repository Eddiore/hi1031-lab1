use Webshop;

db.T_Users.insertOne({
    username: "customer1",
    passwordHash: "test"
});

db.T_Items.insertOne({
    name: "Apple",
    description: "Round and red"
});

db.T_Items.insertOne({
    name: "Pear",
    description: "Oval and green"
});
