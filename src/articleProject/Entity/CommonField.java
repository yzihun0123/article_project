package articleProject.Entity;

import java.time.LocalDateTime;

public class CommonField {
    private LocalDateTime insertedDate;
    private LocalDateTime updatedDate;

    public void insertDate (LocalDateTime insertedDate){
        this.insertedDate = insertedDate;
    }

    public void updateDate (LocalDateTime updatedDate){
        this.updatedDate = updatedDate;
    }

    public LocalDateTime getInsertedDate() {
        return insertedDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setInsertedDate(LocalDateTime insertedDate) {
        this.insertedDate = insertedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
}
