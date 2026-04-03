package com.squareup.exemplar.persistence

import java.time.Instant
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Table
import misk.hibernate.DbRoot
import misk.hibernate.DbTimestampedEntity
import misk.hibernate.Id

@Entity
@Table(name = "users")
class DbUser() : DbRoot<DbUser>, DbTimestampedEntity {

  @javax.persistence.Id
  @GeneratedValue
  @Column(name = "id")
  override lateinit var id: Id<DbUser>

  @Column(name = "created_at", nullable = false)
  override lateinit var created_at: Instant

  @Column(name = "updated_at", nullable = false)
  override lateinit var updated_at: Instant

  @Column(name = "email", nullable = false, unique = true)
  lateinit var email: String

  @Column(name = "name", nullable = false)
  lateinit var name: String

  @Column(name = "is_active", nullable = false)
  var is_active: Boolean = true

  constructor(
    email: String,
    name: String,
    isActive: Boolean = true
  ) : this() {
    val now = Instant.now()
    this.email = email
    this.name = name
    this.is_active = isActive
    this.created_at = now
    this.updated_at = now
  }

  fun toModel(): User {
    return User(
      id = id.id,
      email = email,
      name = name,
      isActive = is_active,
      createdAt = created_at,
      updatedAt = updated_at
    )
  }
}

/**
 * Model class for User entity
 */
data class User(
  val id: Long,
  val email: String,
  val name: String,
  val isActive: Boolean,
  val createdAt: Instant,
  val updatedAt: Instant
)