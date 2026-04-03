package com.squareup.exemplar

import com.squareup.exemplar.persistence.DbUser
import misk.hibernate.HibernateEntityModule
import misk.hibernate.HibernateModule
import misk.inject.KAbstractModule

class ExemplarPersistenceModule(
  private val config: ExemplarConfig,
  private val dataSourceName: String = "exemplar-001"
) : KAbstractModule() {

  override fun configure() {
    install(
      HibernateModule(
        qualifier = ExemplarDbCluster::class,
        readerQualifier = ExemplarReadReplica::class,
        cluster = config.data_source_clusters[dataSourceName]!!
      )
    )

    install(
      object : HibernateEntityModule(ExemplarDbCluster::class) {
        override fun configureHibernate() {
          addEntity<DbUser>()
        }
      }
    )
  }
}