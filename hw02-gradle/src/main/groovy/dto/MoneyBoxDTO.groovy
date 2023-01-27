package dto

class MoneyBoxDTO {
    private Map<String, Integer> boxes
    private String message

    Map<String, Integer> getBoxes() {
        return boxes
    }

    void setBoxes(Map<String, Integer> boxes) {
        this.boxes = boxes
    }

    String getMessage() {
        return message
    }

    void setMessage(String message) {
        this.message = message
    }
}
