package com.lu5je0.state.machine;

@FunctionalInterface
public interface Transition<S extends Enum<S>, E, O> {

    S transition(O operand, E event);

}
