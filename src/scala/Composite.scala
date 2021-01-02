package scala

import part1.{Mailbox, Message}

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scala.jdk.CollectionConverters._

trait AComponent {
  def name: String

  /*def accept: Unit*/
}

class Account(val username: String) extends AComponent {
  override def name: String = username

  /*override def accept: Unit = {}*/

  var mailbox: Mailbox = null

  def getMail: List[Message] = {
    mailbox.updateMail()
    mailbox.listMail().asScala.toList
  }

  def accept(visitorInterface: VisitorInterface): Unit = {
    visitorInterface.visit(this)
  }
}

class Domain(val domain: String) extends AComponent {
  /*override def accept: Unit = {}*/

  var childrenDomain: ListBuffer[AComponent] = new ListBuffer[AComponent]()

  def addChild(child: AComponent): Unit = {
    childrenDomain += child
  }

  def removeChild(child: AComponent): Unit = {
    childrenDomain -= child
  }

  override def name = domain

  def printTree: Unit = {
    printTreeNode("")
  }

  def printTreeNode(c:String): Unit = {
    println(c + "|" + name)
    childrenDomain.foreach(pos => pos match {
      case l: Domain =>
        val tab = c + "|\t"
        l.printTreeNode(tab)
      case l: Account =>
        println(c + "|\t|@" + l.name)
      case _ =>
    })
  }

  def getMail: ListBuffer[Message] = {
    val message: ListBuffer[Message] = new ListBuffer[Message]
    val users: ListBuffer[Account] = getUsers
    users.foreach(account => {
      message.addAll(account.getMail)
    })
    message
  }

  def getUsers: ListBuffer[Account] = {
    val users: ListBuffer[Account] = new ListBuffer[Account]
    childrenDomain.foreach(pos => pos match {
      case l: Domain =>
        users.addAll(l.getUsers)
      case l: Account =>
        users.addOne(l)
    })
    users
  }

  def accept(visitorInterface: VisitorInterface): Unit = {
    visitorInterface.visit(this)
  }
}
