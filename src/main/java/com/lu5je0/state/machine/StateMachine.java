package com.lu5je0.state.machine;

public interface StateMachine<S, T extends Enum<T>, O, E> {

    S getMachineState();

    void doTransition(T type, O operand, E event);

}
