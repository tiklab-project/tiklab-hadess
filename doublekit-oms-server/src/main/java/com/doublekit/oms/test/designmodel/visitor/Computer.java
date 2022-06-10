package com.doublekit.oms.test.designmodel.visitor;

public class Computer {
   ComputerPart cpu= new CPU();
    ComputerPart memory=new Memory();

    public void accept(Visitor v){
        this.cpu.accept(v);
        this.memory.accept(v);
    }
    public static void main(String[] args){
        CorpVisitor corpVisitor = new CorpVisitor();
        new Computer().accept(corpVisitor);
        System.out.println(corpVisitor.totalPrice);
    }


}

abstract class ComputerPart{
    abstract void accept(Visitor v);
    abstract double getPrice();


}

class CPU extends ComputerPart{

    @Override
    void accept(Visitor v) {
        v.visitCpu(this);
    }
    @Override
    double getPrice() {
        return 200;
    }
}
class Memory extends ComputerPart{

    @Override
    void accept(Visitor v) {
        v.visitMemory(this);
    }

    @Override
    double getPrice() {
        return 300;
    }
}

interface Visitor{
    void visitCpu(CPU cpu);
    void visitMemory(Memory memory);
}

class CorpVisitor implements Visitor{
    double totalPrice=0.0;

    @Override
    public void visitCpu(CPU cpu) {
        totalPrice+=cpu.getPrice()*1;
    }

    @Override
    public void visitMemory(Memory memory) {
        totalPrice+=memory.getPrice()*1;
    }
}


