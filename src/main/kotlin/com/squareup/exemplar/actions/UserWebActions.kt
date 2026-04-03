package com.squareup.exemplar.actions

import com.squareup.exemplar.UserApiAccess
import com.squareup.exemplar.persistence.User
import com.squareup.exemplar.persistence.UserService
import jakarta.inject.Inject
import misk.web.Get
import misk.web.Post
import misk.web.Put
import misk.web.Delete
import misk.web.PathParam
import misk.web.RequestBody
import misk.web.ResponseContentType
import misk.web.actions.WebAction
import misk.web.mediatype.MediaTypes

/**
 * Web actions for User CRUD operations to demonstrate Hibernate integration
 */

// Request/Response DTOs
data class CreateUserRequest(
  val email: String,
  val name: String,
  val isActive: Boolean = true
)

data class UpdateUserRequest(
  val name: String? = null,
  val isActive: Boolean? = null
)

data class UserResponse(
  val id: Long,
  val email: String,
  val name: String,
  val isActive: Boolean,
  val createdAt: String,
  val updatedAt: String
) {
  companion object {
    fun from(user: User) = UserResponse(
      id = user.id,
      email = user.email,
      name = user.name,
      isActive = user.isActive,
      createdAt = user.createdAt.toString(),
      updatedAt = user.updatedAt.toString()
    )
  }
}

data class UsersResponse(val users: List<UserResponse>)

data class MessageResponse(val message: String, val success: Boolean = true)

class GetUsersWebAction @Inject constructor(
  private val userService: UserService
) : WebAction {

  @Get("/api/users")
  @ResponseContentType(MediaTypes.APPLICATION_JSON)
  @UserApiAccess
  fun getUsers(): UsersResponse {
    val users = userService.getAllUsers()
    return UsersResponse(users.map { UserResponse.from(it) })
  }
}

class GetUserWebAction @Inject constructor(
  private val userService: UserService
) : WebAction {

  @Get("/api/users/{id}")
  @ResponseContentType(MediaTypes.APPLICATION_JSON)
  @UserApiAccess
  fun getUser(@PathParam id: Long): UserResponse? {
    val user = userService.findUserById(id)
    return user?.let { UserResponse.from(it) }
  }
}

class CreateUserWebAction @Inject constructor(
  private val userService: UserService
) : WebAction {

  @Post("/api/users")
  @ResponseContentType(MediaTypes.APPLICATION_JSON)
  @UserApiAccess
  fun createUser(@RequestBody request: CreateUserRequest): UserResponse {
    val user = userService.createUser(
      email = request.email,
      name = request.name,
      isActive = request.isActive
    )
    return UserResponse.from(user)
  }
}

class UpdateUserWebAction @Inject constructor(
  private val userService: UserService
) : WebAction {

  @Put("/api/users/{id}")
  @ResponseContentType(MediaTypes.APPLICATION_JSON)
  @UserApiAccess
  fun updateUser(
    @PathParam id: Long,
    @RequestBody request: UpdateUserRequest
  ): UserResponse? {
    val user = userService.updateUser(
      id = id,
      name = request.name,
      isActive = request.isActive
    )
    return user?.let { UserResponse.from(it) }
  }
}

class DeleteUserWebAction @Inject constructor(
  private val userService: UserService
) : WebAction {

  @Delete("/api/users/{id}")
  @ResponseContentType(MediaTypes.APPLICATION_JSON)
  @UserApiAccess
  fun deleteUser(@PathParam id: Long): MessageResponse {
    val deleted = userService.deleteUser(id)
    return if (deleted) {
      MessageResponse("User deleted successfully")
    } else {
      MessageResponse("User not found", success = false)
    }
  }
}