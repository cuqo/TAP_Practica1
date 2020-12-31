package scala

import part1.{MailStore, MailSystem, Mailbox, Message}

import scala.collection.mutable.ListBuffer
import scala.jdk.CollectionConverters._

trait AComponent {
  def name: String
}

class Account(val username: String) extends AComponent {
  override def name = username

  def getMail(mailStore: MailStore): List[Message] = {
    mailStore.getMail(name).asScala.toList
  }
}

class Domain(val domain: String) extends AComponent {
  var childrenDomain: ListBuffer[AComponent] = new ListBuffer[AComponent]()

  def addChild(child: AComponent): Unit = {
    childrenDomain += child
  }

  def removeChild(child: AComponent): Unit = {
    childrenDomain -= child
  }

  override def name = domain

  def printTree(c: String): Unit = {
    println(c + "|" + name)
    childrenDomain.foreach(pos => pos match {
      case l: Domain =>
        val tab = c + "|\t";
        l.printTree(tab)
      case l: Account =>
        println(c + "|\t|@" + l.name)
      case _ =>
    })
  }

  def getMail(): List[Message] = {
    println(getUsers())
    return Nil
  }

  def getUsers(): List[String] = {
    childrenDomain.foreach(pos => pos match {
      case l: Domain =>
      l.getUsers()
      case l: Account => println(l.name)
    })
    return Nil
  }
}
