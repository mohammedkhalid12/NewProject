package sa.elm.newpreproject;

public class MedicineCardInfo {
    private String medicineName,about, DaysBox, PillBox, DoseBox, FrequencyBox, txtTime;

    private String img;
    private String id;

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getDaysBox() {
        return DaysBox;
    }

    public void setDaysBox(String daysBox) {
        DaysBox = daysBox;
    }

    public String getPillBox() {
        return PillBox;
    }

    public void setPillBox(String pillBox) {
        PillBox = pillBox;
    }

    public String getDoseBox() {
        return DoseBox;
    }

    public void setDoseBox(String doseBox) {
        DoseBox = doseBox;
    }

    public String getFrequencyBox() {
        return FrequencyBox;
    }

    public void setFrequencyBox(String frequencyBox) {
        FrequencyBox = frequencyBox;
    }

    public String getTxtTime() {
        return txtTime;
    }

    public void setTxtTime(String txtTime) {
        this.txtTime = txtTime;
    }

    public MedicineCardInfo(String medicineName, String img) {

        this.medicineName = medicineName;
        this.img = img;
    }//end constuctor()

    public MedicineCardInfo(String medicineName, String about, String daysBox, String pillBox, String doseBox, String frequencyBox, String txtTime,String img, String id) {
        this.medicineName = medicineName;
        this.about = about;
        DaysBox = daysBox;
        PillBox = pillBox;
        DoseBox = doseBox;
        FrequencyBox = frequencyBox;
       this.img=img;
        this.txtTime = txtTime;
        this.id = id;
    }

    public MedicineCardInfo(String medicineName, String img, String getMedicineNameAr) {
        this.medicineName = medicineName;
        this.img = img;

    }//end constuctor()

    public String  getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String  getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


}//end MedicineCardinfo()
