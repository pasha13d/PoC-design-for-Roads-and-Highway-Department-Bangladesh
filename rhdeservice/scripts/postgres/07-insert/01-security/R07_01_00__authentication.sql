-- Role
insert into Role (oid, roleId, roleDescription, accessJson, status) values ('RHD.ADMIN','RHD.ADMIN','RHD E-Service Admin','[{"name":"Dashboard_TopMenu","url":"/dashboard","icon":"icon-speedometer"}]','Active');
commit;

-- Login
insert into Login (oid, username, password, nameEn, nameBn, email, mobileNo, status, menuJson, roleOid, currentVersion, resetRequired) values ('LN00000000','tanim','4cf6829aa93728e8f3c97df913fb1bfa95fe5810e2933a05943f8312a98d9cf2','Kamruzzaman Tanim','কামরুজ্জামান তানিম','ktanim@gmail.com','01721060879','Active','[{"topmenuid":"UserManagement_TopMenu","leftmenuids":[{"url":"all-user-admin","text":"All_User"}]},{"topmenuid":"IdraManagement_TopMenu","leftmenuids":[{"url":"all-idra-staff-admin","text":"All_User"}]},{"topmenuid":"InsuranceManagement_TopMenu","leftmenuids":[{"url":"all-ins-staff-admin","text":"All_User"}]}]','RHD.ADMIN','1','No');
insert into Login (oid, username, password, nameEn, nameBn, email, mobileNo, status, menuJson, roleOid, currentVersion, resetRequired) values ('LN00000001','mashud','$2a$10$Nj9AB/ffFQX/FEj3KgdmLe9vu6NlVMfBrG6/bsq9zD0eUAlMMJ6o.','Mashud Karim','মাসউদ করিম','mashud.karim85@gmail.com','01717518150','Active','[{"topmenuid":"UserManagement_TopMenu","leftmenuids":[{"url":"all-user-admin","text":"All_User"}]},{"topmenuid":"IdraManagement_TopMenu","leftmenuids":[{"url":"all-idra-staff-admin","text":"All_User"}]},{"topmenuid":"InsuranceManagement_TopMenu","leftmenuids":[{"url":"all-ins-staff-admin","text":"All_User"}]}]','RHD.ADMIN','1','No');
insert into Login (oid, username, password, nameEn, nameBn, email, mobileNo, status, menuJson, roleOid, currentVersion, resetRequired) values ('LN00000002','pasha','$2a$10$Nj9AB/ffFQX/FEj3KgdmLe9vu6NlVMfBrG6/bsq9zD0eUAlMMJ6o.','Pasha','মাহবুব আল ইসলাম','mahabub13d1995@gmail.com','01681416002','Active','[{"topmenuid":"UserManagement_TopMenu","leftmenuids":[{"url":"all-user-admin","text":"All_User"}]},{"topmenuid":"IdraManagement_TopMenu","leftmenuids":[{"url":"all-idra-staff-admin","text":"All_User"}]},{"topmenuid":"InsuranceManagement_TopMenu","leftmenuids":[{"url":"all-ins-staff-admin","text":"All_User"}]}]','RHD.ADMIN','1','No');
insert into Login (oid, username, password, nameEn, nameBn, email, mobileNo, status, menuJson, roleOid, currentVersion, resetRequired) values ('LN00000003','shajir','$2a$10$Nj9AB/ffFQX/FEj3KgdmLe9vu6NlVMfBrG6/bsq9zD0eUAlMMJ6o.','Shajir','সাজির হায়দার','shajir.haiddr@cellosco.pe','01681470970','Active','[{"topmenuid":"UserManagement_TopMenu","leftmenuids":[{"url":"all-user-admin","text":"All_User"}]},{"topmenuid":"IdraManagement_TopMenu","leftmenuids":[{"url":"all-idra-staff-admin","text":"All_User"}]},{"topmenuid":"InsuranceManagement_TopMenu","leftmenuids":[{"url":"all-ins-staff-admin","text":"All_User"}]}]','RHD.ADMIN','1','No');
insert into Login (oid, username, password, nameEn, nameBn, email, mobileNo, status, menuJson, roleOid, currentVersion, resetRequired) values ('LN00000004','muhsin','$2a$10$Nj9AB/ffFQX/FEj3KgdmLe9vu6NlVMfBrG6/bsq9zD0eUAlMMJ6o.','Muhsin','মুহসিন','muhsinur.rahman.chow@spectrum-bd.com','01746953387','Active','[{"topmenuid":"UserManagement_TopMenu","leftmenuids":[{"url":"all-user-admin","text":"All_User"}]},{"topmenuid":"IdraManagement_TopMenu","leftmenuids":[{"url":"all-idra-staff-admin","text":"All_User"}]},{"topmenuid":"InsuranceManagement_TopMenu","leftmenuids":[{"url":"all-ins-staff-admin","text":"All_User"}]}]','RHD.ADMIN','1','No');
commit;

