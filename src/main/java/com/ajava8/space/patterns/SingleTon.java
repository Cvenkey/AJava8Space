package com.ajava8.space.patterns;

//Not necessary to have Singleton instance which is implement cloning
public class SingleTon implements Cloneable {

    private SingleTon(){
        if (singleTonObject != null) {
            throw new IllegalStateException("instance already created.");
        }
    }

    public static SingleTon singleTonObject = new SingleTon();

//    public  static SingleTon getInstance(){
//        if(singleTonObject == null){
//            synchronized (SingleTon.class){
//                 singleTonObject = new SingleTon();
//            }
//        }
//        return singleTonObject;
//    }

//    protected Object readResolve() {
//        return getInstance();
//    }

    @Override
    protected Object clone()  {
       return singleTonObject;
    }
}
