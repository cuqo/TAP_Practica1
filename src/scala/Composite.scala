package scala

import part1.{Mailbox, Message}
import scala.collection.mutable.ListBuffer
import scala.jdk.CollectionConverters._

trait AComponent {
  /**
   * Method that return a String
   * @return string
   */
  def name: String
}

class Account(val username: String) extends AComponent {

  var mailbox: Mailbox = _

  /**
   * Override method that return account name
   * @return account name
   */
  override def name: String = username

  /**
   * Method that get message list of current account
   * @return account message list
   */
  def getMail: List[Message] = {
    mailbox.updateMail()
    mailbox.listMail().asScala.toList
  }

  /**
   * Method that accept the Visitor to interact with Visitor methods
   * @param visitorInterface -> visitor to interact
   */
  def accept(visitorInterface: VisitorInterface): Unit = {
    visitorInterface.visit(this)
  }
}

class Domain(val domain: String) extends AComponent {
  var childrenDomain: ListBuffer[AComponent] = new ListBuffer[AComponent]()

  /**
   * Method that append a child in childrenDomain
   * @param child -> child to append
   */
  def addChild(child: AComponent): Unit = {
    childrenDomain += child
  }

  /**
   * Method that remove a child of childrenDomain
   * @param child -> child to remove
   */
  def removeChild(child: AComponent): Unit = {
    childrenDomain -= child
  }

  /**
   * Override method that return domain name
   * @return domain name
   */
  override def name: String = domain

  /**
   * Method that print the domain
   */
  def printTree(): Unit = printTreeNode("")

  /**
   * Recursive method that print domain name and their child
   * @param c -> tabulate
   */
  def printTreeNode(c: String): Unit = {
    println(c + "|" + name)
    childrenDomain.foreach {
      case l: Domain =>
        val tab = c + "|\t"
        l.printTreeNode(tab)
      case l: Account =>
        println(c + "|\t|@" + l.name)
      case _ =>
    }
  }

  /**
   * Method that get message list of current domain
   * @return domain message list
   */
  def getMail: List[Message] = {
    val message: ListBuffer[Message] = new ListBuffer[Message]
    val users: ListBuffer[Account] = getUsers
    users.foreach(account => {
      message.addAll(account.getMail)
    })
    message.toList
  }

  /**
   * Recursive method that get all domain users including subdomain users
   * @return
   */
  def getUsers: ListBuffer[Account] = {
    val users: ListBuffer[Account] = new ListBuffer[Account]
    childrenDomain.foreach {
      case l: Domain =>
        users.addAll(l.getUsers)
      case l: Account =>
        users.addOne(l)
    }
    users
  }

  /**
   * Method that accept the Visitor to interact with Visitor methods
   * @param visitorInterface -> visitor to interact
   */
  def accept(visitorInterface: VisitorInterface): Unit = {
    visitorInterface.visit(this)
  }
}
