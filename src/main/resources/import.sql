INSERT INTO role VALUES (1,'Admin'),(2,'Doctor'),(3,'Patient');

INSERT INTO hospuser (locked,role_id,lastchangepassword,lastlogondatetime,email,username,userpassword) VALUES (0,1,NULL,'2025-03-14 10:33:17.263707','admin@admin.com','admin','1234'),(0,3,NULL,'2025-03-14 10:33:40.087770','pat@pat.com','patient','1234'),(0,2,NULL,'2025-03-14 10:34:11.024450','doc@doc.com','doctor','1234');
