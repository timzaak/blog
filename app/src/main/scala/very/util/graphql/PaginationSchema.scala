package very.util.graphql

/*
 *
 * -- most compatible design
 * Parameters:
 *
 * first: int, after: $cursor
 * first: int, after: int
 *
 * Response:
 * totalCount
 * edges{
 *    node{
 *      *fields
 *    }
 *    cursor
 * }
 * pageInfo {
 *    endCursor
 *    hasNextPage
 * }
 *
 * https://github.com/graphql/graphql-relay-js/issues/94 is also good
 *
 * https://github.com/sangria-graphql/sangria-relay/blob/master/src/main/scala/sangria/relay/Connection.scala
 * sangrid-relay 已经实现了一个版本
 *
 * 另外 参照 https://dev-blog.apollodata.com/explaining-graphql-connections-c48b7c3d6976
 * pagination 也是 connections
 *
 */
trait PaginationSchema {

}
