CREATE TABLE "locks" (
    "world_name" TEXT NOT NULL,
    "x" INTEGER NOT NULL,
    "y" INTEGER NOT NULL,
    "z" INTEGER NOT NULL,
    "owner_uuid" TEXT NOT NULL,
    "password_hash" TEXT NOT NULL,
    PRIMARY KEY("world_name","x","y","z")
);