package functionaldecomposition.domain

import functionaldecomposition.domain.machine.Machines
import functionaldecomposition.standard.bestsolution.BestSolutionHolder

/**
 * Created by grzegorz.miejski on 27/12/15.
 */
class PartialSolution(machines: Machines, tasksLeft: List[Task]) {

  var partialSolutions: List[PartialSolution] = _

  def createPartialSolutions(): List[PartialSolution] = {
    val nextTask = tasksLeft.head
    val tasksLeftForChildSolutions = tasksLeft.tail

    val newMachinesStates = machines.createPossibleAlternatives(nextTask)

    newMachinesStates.map(PartialSolution(_, tasksLeftForChildSolutions))
  }

  def isLeaf: Boolean = {
    tasksLeft.isEmpty
  }

  def search(): Unit = {

    if (machines.totalCost() <= BestSolutionHolder.currentBestCost && machines.solutionWithinDeadline()) {
      if (isLeaf) {
        BestSolutionHolder.tryUpdate(machines)
      } else {
        partialSolutions = createPartialSolutions()
        partialSolutions.foreach(_.search())
      }
    }
  }
}

object PartialSolution {

  def apply(machines: Machines, tasksLeft: List[Task]) = {
    new PartialSolution(machines, tasksLeft)
  }


}



