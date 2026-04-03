package com.squareup.exemplar

import jakarta.inject.Qualifier

/**
 * Database cluster qualifier for the Exemplar service main database cluster.
 */
@Qualifier
@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
annotation class ExemplarDbCluster

/**
 * Database cluster qualifier for the Exemplar service read replica cluster.
 * Can be the same as the main cluster in development environments.
 */
@Qualifier
@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
annotation class ExemplarReadReplica