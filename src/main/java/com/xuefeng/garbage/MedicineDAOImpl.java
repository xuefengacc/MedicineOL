/*
 * package com.xuefeng.garbage;
 * 
 * import java.util.List;
 * 
 * import javax.persistence.EntityManager; import
 * javax.persistence.PersistenceContext;
 * 
 * import org.springframework.stereotype.Repository; import
 * org.springframework.transaction.annotation.Transactional;
 * 
 * import com.xuefeng.model.Admin; import com.xuefeng.model.Blood; import
 * com.xuefeng.model.BloodBank; import com.xuefeng.model.BloodMember; import
 * com.xuefeng.model.MedMember; import com.xuefeng.model.MedicalShop; import
 * com.xuefeng.model.Medicine; import com.xuefeng.model.Person; import
 * com.xuefeng.model.Standard;
 * 
 * @Transactional
 * 
 * @Repository("dao") public class MedicineDAOImpl implements MedicineDAO {
 * 
 * @PersistenceContext private EntityManager entityManager;
 * 
 * 
 * //--------------Medical Shop Operation------------------ //getAll
 * 
 * @SuppressWarnings("unchecked")
 * 
 * @Override public List<MedicalShop> getAllShops() { String hql =
 * "FROM MedicalShop as atcl ORDER BY atcl.orgId"; return (List<MedicalShop>)
 * entityManager.createQuery(hql).getResultList(); } //getByName
 * 
 * @SuppressWarnings("unchecked")
 * 
 * @Override public List<MedicalShop> getShopByName(String name) { String hql =
 * "FROM MedicalShop WHERE MedicalShop.orgName = :org_name"; return
 * (List<MedicalShop>)entityManager.createQuery(hql) .setParameter("org_name",
 * name).getResultList(); } //getById
 * 
 * @Override public MedicalShop getShop(long shopId) { String hql =
 * "FROM MedicalShop WHERE MedicalShop.orgId=:org_id"; return
 * (MedicalShop)entityManager.createQuery(hql) .setParameter("org_id",
 * shopId).getSingleResult(); } //createShop
 * 
 * @Override public boolean createMedShop(MedicalShop medShop) {
 * entityManager.persist(medShop); return entityManager.contains(medShop); }
 * //Update
 * 
 * @Override public MedicalShop updateMedShop(long shopId, MedicalShop medShop)
 * { MedicalShop shopFromDB = getShop(shopId);
 * shopFromDB.setOrgName(medShop.getOrgName());
 * shopFromDB.setOrgAddress(medShop.getOrgAddress());
 * shopFromDB.setMembers(medShop.getMembers());
 * shopFromDB.setMedicines(medShop.getMedicines());
 * 
 * entityManager.flush(); MedicalShop updatedShop = getShop(shopId); return
 * updatedShop; } //Delete
 * 
 * @Override public boolean deleteMedShop(long shopId) { MedicalShop shop =
 * getShop(shopId); entityManager.remove(shop); if(entityManager.contains(shop))
 * { return false; } else { return true; } }
 * 
 * 
 * //------------Blood Bank Operation---------------------- //getAll
 * 
 * @SuppressWarnings("unchecked")
 * 
 * @Override public List<BloodBank> getAllBanks() { String hql =
 * "FROM BloodBank as actl ORDER BY actl.orgId"; return
 * (List<BloodBank>)entityManager.createQuery(hql).getResultList(); }
 * //getByName
 * 
 * @Override public List<BloodBank> getBankByName(String name) { // TODO
 * Auto-generated method stub return null; } //getById
 * 
 * @Override public BloodBank getBank(long bankId) { String hql =
 * "FROM BloodBank WHERE BloodBank.orgId = :org_id"; return (BloodBank)
 * entityManager.createQuery(hql) .setParameter("orgId",
 * bankId).getSingleResult(); } //create
 * 
 * @Override public boolean createBank(BloodBank bloodBank) {
 * entityManager.persist(bloodBank); return entityManager.contains(bloodBank); }
 * //update
 * 
 * @Override public BloodBank updateBank(long bankId, BloodBank bloodBank) {
 * BloodBank bankFromDB = getBank(bankId);
 * bankFromDB.setOrgName(bloodBank.getOrgName());
 * bankFromDB.setOrgAddress(bloodBank.getOrgAddress());
 * bankFromDB.setMembers(bloodBank.getMembers());
 * bankFromDB.setBloods(bloodBank.getBloods());
 * 
 * entityManager.flush(); BloodBank updatedBank = getBank(bankId); return
 * updatedBank; } //delete
 * 
 * @Override public boolean deleteBank(long bankId) { BloodBank bloodBank =
 * getBank(bankId); entityManager.remove(bloodBank);
 * if(entityManager.contains(bloodBank)) { return true; }else { return false; }
 * }
 * 
 * 
 * //------------------Medicine Operation----------------- //getAll
 * 
 * @SuppressWarnings("unchecked")
 * 
 * @Override public List<Medicine> getAllMed() { String hql =
 * "FROM Medicine as med ORDER BY med.itemId"; return
 * (List<Medicine>)entityManager.createQuery(hql).getResultList(); } //getByName
 * 
 * @Override public List<Medicine> getMedByName(String name) { // TODO
 * Auto-generated method stub return null; } //getById
 * 
 * @Override public Medicine getMedById(long medId) { String hql =
 * "FROM Medicine WHERE Medicine.itemId=:item_id"; return
 * (Medicine)entityManager.createQuery(hql) .setParameter("item_id",
 * medId).getSingleResult(); } //create
 * 
 * @Override public boolean createMed(Medicine med) {
 * entityManager.persist(med); return entityManager.contains(med); } //update
 * 
 * @Override public Medicine updateMed(long medId, Medicine med) { Medicine
 * medFromDB = getMedById(medId); medFromDB.setItemName(med.getItemName());
 * medFromDB.setProducer(med.getProducer()); medFromDB.setPrice(med.getPrice());
 * 
 * entityManager.flush(); Medicine updatedMed = getMedById(medId); return
 * updatedMed; } //delete
 * 
 * @Override public boolean deleteMed(long medId) { Medicine med =
 * getMedById(medId); entityManager.remove(med); if(entityManager.contains(med))
 * { return false; }else { return true; } }
 * 
 * 
 * //------------------Blood Operation----------------------- //getAll
 * 
 * @SuppressWarnings("unchecked")
 * 
 * @Override public List<Blood> getAllBlood() { String hql =
 * "FROM Blood ORDER BY Blood.itemId"; return
 * (List<Blood>)entityManager.createQuery(hql).getResultList(); } //getByDetail
 * 
 * @Override public List<Blood> getBloodByDetail(String detail) { // TODO
 * Auto-generated method stub return null; } //getById
 * 
 * @Override public Blood getBloodById(long bloodId) { String hql =
 * "FROM Blood WHERE Blood.itemId = :item_id"; return
 * (Blood)entityManager.createQuery(hql) .setParameter("item_id",
 * bloodId).getSingleResult(); } //create
 * 
 * @Override public boolean createBlood(Blood blood) {
 * entityManager.persist(blood); return entityManager.contains(blood); }
 * //update
 * 
 * @Override public Blood updateBlood(long bloodId, Blood blood) { Blood
 * bloodFromDB = getBloodById(bloodId);
 * bloodFromDB.setDetails(bloodFromDB.getDetails());
 * bloodFromDB.setSize(bloodFromDB.getSize());
 * 
 * entityManager.flush(); Blood updatedBlood = getBloodById(bloodId); return
 * updatedBlood; } //delete
 * 
 * @Override public boolean deleteBlood(long bloodId) { Blood blood =
 * getBloodById(bloodId); entityManager.remove(blood);
 * if(entityManager.contains(blood)) { return false; }else { return true; } }
 * 
 * 
 * User Operation
 * 
 * @Override public List<Person> getAllPerson() { // TODO Auto-generated method
 * stub return null; }
 * 
 * @Override public List<Person> getPersonByRole(String role) { // TODO
 * Auto-generated method stub return null; }
 * 
 * @Override public List<Admin> getAllAdmin() { // TODO Auto-generated method
 * stub return null; }
 * 
 * @Override public boolean createAdmin(Admin admin) { // TODO Auto-generated
 * method stub return true; }
 * 
 * @Override public Admin updateAdmin(long adminId, Admin admin) { // TODO
 * Auto-generated method stub return null; }
 * 
 * @Override public boolean deleteAdmin(Admin adminId) { // TODO Auto-generated
 * method stub return false; }
 * 
 * @Override public List<Standard> getAllStandard() { // TODO Auto-generated
 * method stub return null; }
 * 
 * @Override public boolean createStandard(Standard user) { // TODO
 * Auto-generated method stub return true; }
 * 
 * @Override public Standard updateStandard(long userId, Standard user) { //
 * TODO Auto-generated method stub return null; }
 * 
 * @Override public boolean deleteStandard(Standard userId) { // TODO
 * Auto-generated method stub return false; }
 * 
 * @Override public List<MedMember> getAllMedMember() { // TODO Auto-generated
 * method stub return null; }
 * 
 * @Override public boolean createMedMember(MedMember medMember) { // TODO
 * Auto-generated method stub return true; }
 * 
 * @Override public MedMember updateMedMember(long medMemberId, MedMember
 * medMember) { // TODO Auto-generated method stub return null; }
 * 
 * @Override public boolean deleteMedMember(MedMember medMemberId) { // TODO
 * Auto-generated method stub return false; }
 * 
 * @Override public List<BloodMember> getAllBloodMember() { // TODO
 * Auto-generated method stub return null; }
 * 
 * @Override public boolean createBloodMember(BloodMember bloodMember) { // TODO
 * Auto-generated method stub return true; }
 * 
 * @Override public BloodMember updateBloodMember(long bloodMemberId,
 * BloodMember bloodMember) { // TODO Auto-generated method stub return null; }
 * 
 * @Override public boolean deleteBloodMember(String bloodMemberId) { // TODO
 * Auto-generated method stub return false; }
 * 
 * //---------------------------Common Method------------------------------
 * 
 * 
 * }
 */