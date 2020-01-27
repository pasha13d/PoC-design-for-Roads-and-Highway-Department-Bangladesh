-- Designation
insert into Designation (oid, designationBn, designationEn) values ('DEG01','সংগঠন','Organization');
insert into Designation (oid, designationBn, designationEn) values ('DEG02','ভিআইপি','VIP');
insert into Designation (oid, designationBn, designationEn) values ('DEG03','সংসদ সদস্য','Member of Parliament');
insert into Designation (oid, designationBn, designationEn) values ('DEG04','প্রতিমন্ত্রী','Minister of State');
insert into Designation (oid, designationBn, designationEn) values ('DEG05','সচিব (সড়ক মন্ত্রনালয়)','Secretary of Roads Ministry');
insert into Designation (oid, designationBn, designationEn) values ('DEG06','প্রধান প্রকৌশলী (সড়ক মন্ত্রনালয়)','Chief Engineer of RHD');
insert into Designation (oid, designationBn, designationEn) values ('DEG07','প্রধান প্রকৌশলী (অতিরিক্ত)','Additional Chief Engineer');
insert into Designation (oid, designationBn, designationEn) values ('DEG08','নির্বাহী প্রকৌশলী','Executive Engineer');
insert into Designation (oid, designationBn, designationEn) values ('DEG09','সুপারিনটেনডেন্ট ইঞ্জিনিয়ার','Superintendent Engineer');
insert into Designation (oid, designationBn, designationEn) values ('DEG10','কম্পিউটার অপারেটর','Computer Operator');
insert into Designation (oid, designationBn, designationEn) values ('DEG11','অন্যান্য (সরকারী ব্যক্তি)','Other Official Person');
insert into Designation (oid, designationBn, designationEn) values ('DEG12','সিস্টেম এডমিনিস্ট্রেটর','System Administrator');
insert into Designation (oid, designationBn, designationEn) values ('DEG13','সচিব (পরিবেশ মন্ত্রনালয়)','Secretary of Environment Ministry');
insert into Designation (oid, designationBn, designationEn) values ('DEG14','সচিব (অন্যান্য মন্ত্রনালয়)','Secretary of Others Ministry');
insert into Designation (oid, designationBn, designationEn) values ('DEG15','প্রধানমন্ত্রীর কার্যালয়','Prime Minister Office');
insert into Designation (oid, designationBn, designationEn) values ('DEG16','ঠিকাদার','Work Contractor');
commit;

-- Division
insert into Division (oid, divisionNameEn, divisionNameBn) values ('DV01','Dhaka','ঢাকা');
insert into Division (oid, divisionNameEn, divisionNameBn) values ('DV02','Chattogram','চট্টগ্রাম');
insert into Division (oid, divisionNameEn, divisionNameBn) values ('DV03','Rajsahi','রাজশাহী');
insert into Division (oid, divisionNameEn, divisionNameBn) values ('DV04','Khulna','খুলনা');
insert into Division (oid, divisionNameEn, divisionNameBn) values ('DV05','Sylhet','সিলেট');
insert into Division (oid, divisionNameEn, divisionNameBn) values ('DV06','Barishal','বরিশাল');
insert into Division (oid, divisionNameEn, divisionNameBn) values ('DV07','Mymensingh','ময়মনসিংহ');
insert into Division (oid, divisionNameEn, divisionNameBn) values ('DV08','Rangpur','রংপুর');
commit;

-- District
insert into District (oid, districtNameEn, districtNameBn, divisionOid) values ('DS01','Dhaka','ঢাকা','DV01');
insert into District (oid, districtNameEn, districtNameBn, divisionOid) values ('DS02','Narayanganj','নারায়নগনজ','DV01');
insert into District (oid, districtNameEn, districtNameBn, divisionOid) values ('DS03','Jamalpur','জামালপুর','DV01');
insert into District (oid, districtNameEn, districtNameBn, divisionOid) values ('DS04','Comilla','কুমিললা','DV02');
insert into District (oid, districtNameEn, districtNameBn, divisionOid) values ('DS05','Munsiganj','মুনশি','DV01');
insert into District (oid, districtNameEn, districtNameBn, divisionOid) values ('DS06','Norsingdi','নারাসিংদী','DV01');
insert into District (oid, districtNameEn, districtNameBn, divisionOid) values ('DS07','Chattogram','চট্টগ্রাম','DV02');
insert into District (oid, districtNameEn, districtNameBn, divisionOid) values ('DS08','Rajsahi','রাজশাহী','DV03');
insert into District (oid, districtNameEn, districtNameBn, divisionOid) values ('DS09','Khulna','খুলনা','DV04');
insert into District (oid, districtNameEn, districtNameBn, divisionOid) values ('DS10','Sylhet','সিলেট','DV05');
insert into District (oid, districtNameEn, districtNameBn, divisionOid) values ('DS11','Barishal','বরিশাল','DV06');
insert into District (oid, districtNameEn, districtNameBn, divisionOid) values ('DS12','Mymensingh','ময়মনসিংহ','DV07');
insert into District (oid, districtNameEn, districtNameBn, divisionOid) values ('DS13','Rangpur','রংপুর','DV08');
commit;

-- Upazilla
insert into Upazilla (oid, upazillaNameEn, upazillaNameBn, districtOid) values ('UZ01','Dhaka','ঢাকা','DS01');
insert into Upazilla (oid, upazillaNameEn, upazillaNameBn, districtOid) values ('UZ02','Motijheel','মতিঝিল','DS01');
insert into Upazilla (oid, upazillaNameEn, upazillaNameBn, districtOid) values ('UZ03','Rampura','রামপুরা','DS01');
insert into Upazilla (oid, upazillaNameEn, upazillaNameBn, districtOid) values ('UZ04','Uttara','উত্তরা','DS01');
commit;


