DROP TABLE IF EXISTS "game";
DROP TABLE IF EXISTS "board";

CREATE TABLE "game" (
	"id"	INTEGER,
	"sqltime"	TIMESTAMP NOT NULL DEFAULT (STRFTIME('%Y-%m-%d %H:%M:%f', 'NOW')),
    "next_board_seqnr" INTEGER NOT NULL DEFAULT (0),
	 PRIMARY KEY("id" AUTOINCREMENT)
);

CREATE TABLE "board" (
	"id"	INTEGER NOT NULL,
	"game_id"	INTEGER NOT NULL,
	"sqltime"	TIMESTAMP NOT NULL DEFAULT (STRFTIME('%Y-%m-%d %H:%M:%f', 'NOW')),
	"seqnr"	 INTEGER NOT NULL,
	"turn"	INTEGER NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT),
	FOREIGN KEY("game_id") REFERENCES "game"("id")
);



