package scala

import part1.Message
import java.util.function.Predicate
import scala.collection.mutable.ListBuffer
import scala.jdk.CollectionConverters._


abstract class VisitorInterface {

  /**
   * Method that performs and operation on an Account
   * @param account -> current account
   */
  def visit(account: Account): Unit

  /**
   * Method that performs and operation on a Domain
   * @param domain -> current domain
   */
  def visit(domain: Domain): Unit
}

class FilterVisitor(predicate: Predicate[Message]) extends VisitorInterface {
  var messages: ListBuffer[Message] = ListBuffer[Message]()

  /**
   * Override method that filter account message list with predicate parameter and add this filtered message list into messages
   * @param account -> current account
   */
  override def visit(account: Account): Unit = {
    messages.addAll(account.mailbox.filterUserMail(predicate).asScala.toList)
  }

  /**
   * Override method that filter domain message list with predicate parameter and add this filtered message list into messages
   * @param domain -> current domain
   */
  override def visit(domain: Domain): Unit = {
    val message: ListBuffer[Message] = new ListBuffer[Message]
    val users: ListBuffer[Account] = domain.getUsers

    users.foreach(account => {
      message.addAll(account.mailbox.filterUserMail(predicate).asScala.toList)
    })
    messages = message
  }
}

class CounterVisitor() extends VisitorInterface {
  var users: Int = 0
  var domains: Int = 0

  /**
   * Override method that count account
   * @param account -> current account
   */
  override def visit(account: Account): Unit = {
    users += 1
  }

  /**
   * Recursive override method that count subdomains of this domain
   * @param domain -> current domain
   */
  override def visit(domain: Domain): Unit = {
    domain.childrenDomain.foreach {
      case l: Domain =>
        visit(l)
        domains += 1
      case l: Account =>
        visit(l)
    }
  }
}

class FoldFilterVisitor[A](acc: A, op: (A, Message) => A, predCond: Predicate[Account]) extends VisitorInterface {
  var users: Map[String, A] = Map()

  /**
   * Override method that apply a (A, Message) => A operation to current account
   * @param account -> current account
   */
  override def visit(account: Account): Unit = {
    val list: List[Message] = account.getMail
    users += (account.username -> list.foldLeft(acc)(op))
  }

  /**
   * Recursive override method that visit all subdomains/accounts of each domain and apply a (A, Message) => A operation
   * @param domain -> current domain
   */
  override def visit(domain: Domain): Unit = {
    domain.childrenDomain.foreach {
      case l: Domain =>
        visit(l)
      case l: Account =>
        if (predCond.test(l)) {
          visit(l)
        }
    }
  }
}

class TransformerVisitor(op: List[Message] => List[Message]) extends VisitorInterface {
  var messages: ListBuffer[Message] = ListBuffer[Message]()
  var m: List[Message] = List()

  /**
   * Override method that apply a List[Message] => List[Message] operation on account message list and added to messages
   * @param account -> current account
   */
  override def visit(account: Account): Unit = {
    m = op(account.getMail)
    messages.addAll(m)
  }

  /**
   * Override method that apply a List[Message] => List[Message] operation on domain message list and added to messages
   * @param domain -> current domain
   */
  override def visit(domain: Domain): Unit = {
    m = op(domain.getMail)
    messages.addAll(m)
  }
}
