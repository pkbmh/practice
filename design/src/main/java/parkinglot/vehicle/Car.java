package parkinglot.vehicle;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Car implements Vehicle {
    private final String number;
    private final String color;

    public String getColor() {
        return color;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Vehicle && obj instanceof Car) {
            Car car = (Car) obj;
            return (car.color.equals(color) && car.number.equals(number)) ;
        }
        return false;
    }
}
