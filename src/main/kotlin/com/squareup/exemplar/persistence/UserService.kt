package com.squareup.exemplar.persistence

import com.squareup.exemplar.ExemplarDbCluster
import jakarta.inject.Inject
import jakarta.inject.Singleton
import misk.hibernate.Id
import misk.hibernate.Transacter
import misk.hibernate.load

@Singleton
class UserService @Inject constructor(
  @ExemplarDbCluster private val transacter: Transacter
) {

  fun createUser(email: String, name: String, isActive: Boolean = true): User {
    return transacter.transaction { session ->
      val dbUser = DbUser(email = email, name = name, isActive = isActive)
      session.save(dbUser)
      dbUser.toModel()
    }
  }

  fun findUserById(id: Long): User? {
    return transacter.transaction { session ->
      session.load<DbUser>(Id(id))?.toModel()
    }
  }

  fun findUserByEmail(email: String): User? {
    return transacter.transaction { session ->
      session.hibernateSession.createQuery("FROM DbUser WHERE email = :email", DbUser::class.java)
        .setParameter("email", email)
        .uniqueResult()
        ?.toModel()
    }
  }

  fun getAllUsers(): List<User> {
    return transacter.transaction { session ->
      session.hibernateSession.createQuery("FROM DbUser", DbUser::class.java)
        .list()
        .map { it.toModel() }
    }
  }

  fun updateUser(id: Long, name: String? = null, isActive: Boolean? = null): User? {
    return transacter.transaction { session ->
      val dbUser = session.load<DbUser>(Id(id))
      if (dbUser != null) {
        name?.let { dbUser.name = it }
        isActive?.let { dbUser.is_active = it }
        session.save(dbUser)
        dbUser.toModel()
      } else {
        null
      }
    }
  }

  fun deleteUser(id: Long): Boolean {
    return transacter.transaction { session ->
      val dbUser = session.load<DbUser>(Id(id))
      if (dbUser != null) {
        session.delete(dbUser)
        true
      } else {
        false
      }
    }
  }
}