package com.lu5je0.state.machine;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StateMachineFactory<T extends Enum<T>, S extends Enum<S>, E, O> {

    private final Map<S, Map<T, Transition<S, E, O>>> transitionTable = new HashMap<>();

    public StateMachineFactory<T, S, E, O> addTransition(S preState, S postState, T eventType, Transition<S, E, O> transition) {
        Map<T, Transition<S, E, O>> transitionMap = transitionTable.computeIfAbsent(preState, s -> new HashMap<>());
        transitionMap.put(eventType, (operand, event) -> {
            S state = transition.transition(operand, event);
            if (!postState.equals(state)) {
                throw new RuntimeException("wrong transition");
            }
            return state;
        });
        return this;
    }

    public StateMachine<S, T, O, E> get(S initState) {
        return new InternalStateMachine(initState);
    }

    private class InternalStateMachine implements StateMachine<S, T, O, E> {

        private S state;

        public InternalStateMachine(S initState) {
            this.state = initState;
        }

        @Override
        public void doTransition(T type, O operand, E event) {
            Map<T, Transition<S, E, O>> transitionMap = transitionTable.get(state);
            Transition<S, E, O> transition = Optional.ofNullable(transitionMap)
                    .map(map -> map.get(type))
                    .orElseThrow(() -> new RuntimeException("非法的type"));
            state = transition.transition(operand, event);
        }

        @Override
        public S getMachineState() {
            return state;
        }

    }

}
