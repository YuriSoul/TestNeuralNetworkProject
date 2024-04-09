import java.math.BigDecimal;
import java.math.RoundingMode;


public class predictiveMachine {
    //входные данные (величины)
    double constMiles = 62.137; //константное значение в милях
    double constKilometer = 100.0; //константное значение в километрах
    double trueValueCoeff = 0.62137; //константное (верное) значение коэффициента

    //расчетные данные
    double errorValue = -1; //величина ошибка расчетного значения миль по отношению к константному значению миль
    double startTestCoeff = 0.0; //начальное тестовое значение коэффициента
    double currentCoeff = 0.0; //текущее значение коеффициента
    double stepCoeff = 0.1; //шаг для расчета тестового значения коеффициента
    double calculatedValueMiles; //расчетное значение миль

    //округление числа до нужного количества знаков после запятой
    double roundNum(double value){
        int scale = 5;
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(scale, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }

    //расчет тестового значения коеффициента
    void calcTestCoeff(){
        currentCoeff = startTestCoeff + stepCoeff;
        currentCoeff = roundNum(currentCoeff);
        System.out.println(startTestCoeff + " начальное значение коефф + "
                    + stepCoeff + " шаг " + " = " + currentCoeff + " текущее значение коефф");

        //System.out.println(currentCoeff);
        startTestCoeff = currentCoeff;
    }

    //расчет значения миль
    //километры * К = мили (где К - коэффициент перевода километров в мили)
    void сalcValueMiles(){
        calculatedValueMiles = constKilometer * currentCoeff;
        System.out.println(constKilometer + " km * " + currentCoeff + " коефф " + " = " + calculatedValueMiles + " миль");
    }

    //вычисление ошибки расчетного значения миль
    void calcValueMilesError(){
        errorValue = constMiles - calculatedValueMiles; //константное значение миль - расчетное значение миль
        errorValue = roundNum(errorValue);
        System.out.println(constMiles + " миль (конст) - " + calculatedValueMiles + " миль (расчет) " + " = "
                + errorValue + " (ошибка) миль");
    }

    //проверка ошибки расчетного значения миль
    boolean checkErrorValue(){
        if(errorValue < 0) return true;
        else return false;
    }

    //перeсчет текущего коеффициента и шага
    void recalcСoeffendStep(){
        startTestCoeff = currentCoeff - stepCoeff; //пересчет коеффициента
        stepCoeff = stepCoeff/10; //пересчет шага
    }

    //получить значение ошибки
    double getErrorValue(){
        return errorValue;
    }

    //получить значение коеффициента
    double getCurrentCoeff(){
        return currentCoeff;
    }

    public static void main(String[] args) {
        predictiveMachine pm = new predictiveMachine();
        int count = 0;
        while (pm.errorValue != 0){
            System.out.println();
            System.out.println("Проход " + (count+1));
            System.out.println();

            pm.calcTestCoeff();
            pm.сalcValueMiles();
            pm.calcValueMilesError();
            if(pm.checkErrorValue() == true) pm.recalcСoeffendStep();
            count++;
        }
        System.out.println("Найденное значение коеффициента = " + pm.getCurrentCoeff());
    }
}
