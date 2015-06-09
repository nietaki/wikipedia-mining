package analysis

/**
 * a class for storing the state during the DFS traversal of the links tree
 *
 * parentArticleNames is a collection of names of articles linking to current article
 *
 * weight is the count of articles that passed through the current one on the way to "Philosophy" (or whatever else is
 * set as the root node)
 *
 * state is the state of the article in the search - to prevent looping the algorithm
 */
class EntryProperties {
  var parentEntryNames: List[String] = List()
  var weight: Int = 1;
  var state: DFSState = DFSState.NotVisited;

  def addParentEntry(entryName: String) = {
    parentEntryNames = entryName :: parentEntryNames
  }
}
