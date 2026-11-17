BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "cliente" (
	"id"	INTEGER,
	"nombre"	TEXT NOT NULL,
	"apellido"	TEXT NOT NULL,
	"numero_documento"	INTEGER NOT NULL,
	"correo_electronico"	TEXT,
	"telefono"	TEXT,
	PRIMARY KEY("id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "habitacion" (
	"id"	INTEGER,
	"numero_hab"	INTEGER NOT NULL UNIQUE,
	"piso"	INTEGER NOT NULL,
	"tipo"	TEXT NOT NULL,
	"precio"	REAL NOT NULL,
	"estado"	TEXT NOT NULL DEFAULT 'Disponible',
	PRIMARY KEY("id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "reserva" (
	"id"	INTEGER,
	"cliente_id"	INTEGER NOT NULL,
	"habitacion_id"	INTEGER NOT NULL,
	"fecha_entrada"	TEXT NOT NULL,
	"fecha_salida"	TEXT NOT NULL,
	"estado"	TEXT NOT NULL DEFAULT 'Pendiente',
	PRIMARY KEY("id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "usuario" (
	"idusuario"	INTEGER,
	"user"	TEXT NOT NULL,
	"password"	TEXT NOT NULL,
	"tipoUser"	TEXT NOT NULL,
	PRIMARY KEY("idusuario" AUTOINCREMENT)
);
COMMIT;
