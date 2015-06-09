package analysis

/**
 * Created by nietaki on 09.06.15.
 */
sealed trait DFSState {

}

object DFSState {
  case object NotVisited extends DFSState
  case object CurrentlyVisiting extends DFSState
  case object Done extends DFSState

}

