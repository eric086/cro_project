var url = "mongodb://localhost:27017/cro";

var db = connect(url);

db.auth('cro', 'cro_password');

db = db.getSiblingDB("cro")

db.createUser({
    user: 'cro_user',
    pwd: '123456',
    roles: [
        {
            role: 'readWrite',
            db: 'cro',
        },
    ],
});