package Vjezba3;



public final class Sensor {
	private short value;
	private short factor;
	private double minValue;
	private double maxValue;
	private String unitOfMeasure;
	
        Sensor(int factor, double minValue,double maxValue,String unitOfMeasure){ 
		this.value = generateRandomValue();
		this.factor = (short)factor;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.unitOfMeasure = unitOfMeasure;
	}
	short generateRandomValue() {
		return (short) ( this.minValue + Math.random() * (this.maxValue-this.minValue));
	}
        
        String getUnitOfMeasure() {
            return this.unitOfMeasure;
        }

}
