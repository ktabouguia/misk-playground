package com.squareup.exemplar

import com.squareup.exemplar.actions.DownloadAFileWebAction
import com.squareup.exemplar.actions.EchoFormAction
import com.squareup.exemplar.actions.HelloWebAction
import com.squareup.exemplar.actions.HelloWebGrpcAction
import com.squareup.exemplar.actions.HelloWebPostAction
import com.squareup.exemplar.actions.HelloWebProtoAction
import com.squareup.exemplar.actions.LeaseAcquireWebAction
import com.squareup.exemplar.actions.LeaseCheckWebAction
import com.squareup.exemplar.actions.CreateUserWebAction
import com.squareup.exemplar.actions.DeleteUserWebAction
import com.squareup.exemplar.actions.GetUserWebAction
import com.squareup.exemplar.actions.GetUsersWebAction
import com.squareup.exemplar.actions.UpdateUserWebAction
import misk.inject.KAbstractModule
import misk.web.WebActionModule

class ExemplarWebActionsModule : KAbstractModule() {
  override fun configure() {
    install(WebActionModule.create<HelloWebGrpcAction>())
    install(WebActionModule.create<HelloWebAction>())
    install(WebActionModule.create<HelloWebPostAction>())
    install(WebActionModule.create<EchoFormAction>())
    install(WebActionModule.create<HelloWebProtoAction>())
    install(WebActionModule.create<DownloadAFileWebAction>())
    install(WebActionModule.create<LeaseAcquireWebAction>())
    install(WebActionModule.create<LeaseCheckWebAction>())

    // User CRUD operations to demonstrate Hibernate integration
    install(WebActionModule.create<GetUsersWebAction>())
    install(WebActionModule.create<GetUserWebAction>())
    install(WebActionModule.create<CreateUserWebAction>())
    install(WebActionModule.create<UpdateUserWebAction>())
    install(WebActionModule.create<DeleteUserWebAction>())
  }
}
