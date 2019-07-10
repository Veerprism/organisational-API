CREATE TABLE "thecompany" (
	"id" serial NOT NULL,
	"name" character varying NOT NULL,
	"description" character varying,
	"totaldepartments" int NOT NULL,
	"totalemployees" int,
	"totalnews" int,
	"creation" timestamp with time zone NOT NULL,
	CONSTRAINT "thecompany_pk" PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "employees" (
	"id" serial NOT NULL,
	"empname" character varying,
	"position" character varying NOT NULL,
	"departmentid" int NOT NULL,
	"creation" timestamp with time zone NOT NULL,
	CONSTRAINT "employees_pk" PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "departments" (
	"id" serial NOT NULL,
	"name" character varying NOT NULL,
	"description" character varying,
	"totalemployees" int,
	"totalnews" int,
	"creation" timestamp with time zone,
	CONSTRAINT "departments_pk" PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "deptnews" (
	"id" serial NOT NULL,
	"departmentid" bigint NOT NULL,
	"news" character varying,
	"creation" timestamp with time zone NOT NULL,
	CONSTRAINT "deptnews_pk" PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "departmentnews" (
	"newsid" serial NOT NULL,
	"actualnews" character varying NOT NULL,
	"departmentid" integer NOT NULL,
	"creation" timestamp with time zone NOT NULL,
	CONSTRAINT "departmentnews_pk" PRIMARY KEY ("newsid")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "admins" (
	"id" serial NOT NULL,
	"username" character varying NOT NULL,
	"password" character varying,
	CONSTRAINT "admins_pk" PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "companynews" (
	"newsid" serial NOT NULL,
	"actualnews" character varying,
	"companyid" int NOT NULL,
	"creation" timestamp with time zone NOT NULL,
	CONSTRAINT "companynews_pk" PRIMARY KEY ("newsid")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "roles" (
	"id" serial NOT NULL,
	"employeeid" bigint NOT NULL,
	"roles" character varying,
	CONSTRAINT "roles_pk" PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);




ALTER TABLE "employees" ADD CONSTRAINT "employees_fk0" FOREIGN KEY ("departmentid") REFERENCES "departments"("id") ON DELETE CASCADE ON UPDATE CASCADE;


ALTER TABLE "deptnews" ADD CONSTRAINT "deptnews_fk0" FOREIGN KEY ("departmentid") REFERENCES "departments"("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "departmentnews" ADD CONSTRAINT "departmentnews_fk0" FOREIGN KEY ("departmentid") REFERENCES "departments"("id") ON DELETE CASCADE ON UPDATE CASCADE;


ALTER TABLE "companynews" ADD CONSTRAINT "companynews_fk0" FOREIGN KEY ("companyid") REFERENCES "thecompany"("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "roles" ADD CONSTRAINT "roles_fk0" FOREIGN KEY ("employeeid") REFERENCES "employees"("id") ON DELETE CASCADE ON UPDATE CASCADE;

