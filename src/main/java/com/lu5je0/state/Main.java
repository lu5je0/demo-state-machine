package com.lu5je0.state;

import com.lu5je0.state.machine.StateMachine;
import com.lu5je0.state.machine.StateMachineFactory;
import com.lu5je0.state.test.OrderContext;
import com.lu5je0.state.test.OrderEvent;
import com.lu5je0.state.test.OrderEventTypeEnum;
import com.lu5je0.state.test.OrderStateEnum;

public class Main {

    public static void main(String[] args) {
        StateMachineFactory<OrderEventTypeEnum, OrderStateEnum, OrderEvent, OrderContext> stateMachineFactory = new StateMachineFactory<>();

        stateMachineFactory.addTransition(OrderStateEnum.CREATED, OrderStateEnum.PAYED, OrderEventTypeEnum.PAY, (operand, event) -> {
            System.out.println("付款");
            return OrderStateEnum.PAYED;
        });

        stateMachineFactory.addTransition(OrderStateEnum.PAYED, OrderStateEnum.CANCELED, OrderEventTypeEnum.CANCEL, (operand, event) -> {
            System.out.println("退款");
            return OrderStateEnum.CANCELED;
        });

        stateMachineFactory.addTransition(OrderStateEnum.PAYED, OrderStateEnum.SHIP, OrderEventTypeEnum.SHIP, (operand, event) -> {
            System.out.println("发货");
            return OrderStateEnum.SHIP;
        });

        stateMachineFactory.addTransition(OrderStateEnum.SHIP, OrderStateEnum.CANCELED, OrderEventTypeEnum.CANCEL, (operand, event) -> {
            System.out.println("退货退款");
            return OrderStateEnum.CANCELED;
        });

        StateMachine<OrderStateEnum, OrderEventTypeEnum, OrderContext, OrderEvent> stateMachine = stateMachineFactory.get(OrderStateEnum.CREATED);

        OrderContext operand = new OrderContext();
        OrderEvent event = new OrderEvent();

        stateMachine.doTransition(OrderEventTypeEnum.PAY, operand, event);
        // stateMachine.doTransition(OrderEventTypeEnum.PAY, operand, event);
        // stateMachine.doTransition(OrderEventTypeEnum.SHIP, operand, event);
        stateMachine.doTransition(OrderEventTypeEnum.CANCEL, operand, event);
    }

}
