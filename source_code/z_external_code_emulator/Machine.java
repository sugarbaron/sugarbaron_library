/* author: sugarbaron ([sugarbaron_bicycles] e-mail:sugarbaron1@mail.ru)]
   date: 11.09.2016 */
package z_external_code_emulator;

//[my bicycles]
import ru.sugarbaron_bicycles.library.state_machine.*;

final class Machine{
  //data_section_______________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  private StateMachine stateMachine;
  private StateMachineState initialState;
  private StateMachineState workState;
  private StateMachineState exitState;

  //constructors_section_______________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  Machine(){
    buildMachine();
  }

  private void buildMachine(){
    stateMachine = StateMachine.createNew();
    createStates(stateMachine);
    createSignals(stateMachine);
    constructMachineStructure(stateMachine);
    return;
  }

  private void createStates(StateMachine stateMachine){
    initialState = new InitialState(stateMachine);
    workState = new WorkState(stateMachine);
    exitState = new ExitState(stateMachine);
    return;
  }

  private void createSignals(StateMachine stateMachine){
    for(SignalsNames signalName: SignalsNames.values()){
      stateMachine.createSignal(signalName);
    }
    return;
  }

  private void constructMachineStructure(StateMachine stateMachine){
    StateMachineSignal startSignal = stateMachine.getSignal(SignalsNames.START);
    StateMachineSignal workSignal  = stateMachine.getSignal(SignalsNames.WORK);
    StateMachineSignal endSignal = stateMachine.getSignal(SignalsNames.END);

    stateMachine.setJump(initialState, initialState, startSignal);
    stateMachine.setJump(initialState, workState, workSignal);
    stateMachine.setJump(workState, exitState, endSignal);

    stateMachine.setStart(initialState);
    stateMachine.setNextStepSignal(startSignal);
    return;
  }

  //methods_section____________________________________________________________
  /////////////////////////////////////////////////////////////////////////////
  void makeStep()
  throws Exception{
    stateMachine.makeStep();
    return;
  }

  boolean isStillWorking(){
    boolean isStillWorking = true;
    StateMachineState currentState = stateMachine.getCurrentState();
    if(currentState == exitState){
      isStillWorking = false;
    }
    return isStillWorking;
  }

  StateMachineState getPreviousState(){
    StateMachineState previousState = stateMachine.getPreviousState();
    return previousState;
  }
}