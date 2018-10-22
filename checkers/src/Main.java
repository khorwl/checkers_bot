import primitives.Field;

public class Main {

    public static void main(String[] args) {
        var field = new Field(5, 7);
        for(var i = 0; i < field.getHeight(); i++){
            for (var j = 0; j < field.getWidth(); j++){
                System.out.print(field.getCell(i, j) + " ");
            }
            System.out.println();
        }

    }
}
