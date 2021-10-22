<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time"%>
<!DOCTYPE html>
<html lang="ko">

<%@include file="../includes/header.jsp"%>

<body id="page-top">

  <!-- Page Wrapper -->
  <div id="wrapper">
    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

      <!-- Main Content -->
      <div id="content">

        <!-- topbar도 별도 파일에 분리하자! -->
        <%@include file="../includes/topbar.jsp"%>

        <!-- Begin Page Content -->
        <div class="container-fluid">

          <!-- Page Heading -->
          <h1 class="h3 mb-2 text-gray-800">Tables</h1>

          <!-- DataTales Example -->
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <div class="form-row float-left">
                <div class="font-weight-bold text-primary">Board List Page</div>
              </div>
              <div class="form-row float-right">
                <button id="regBtn" type="button" class="btn btn-outline-primary btn-sm btn-block">Register New Board</button>
              </div>
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                  <thead>
                    <tr>
                      <th>#번호</th>
                      <th>제목</th>
                      <th>작성자</th>
                      <th>작성일</th>
                      <th>수정일</th>
                    </tr>
                  </thead>
                  <tbody>
                    <c:forEach items="${list}" var="board">
                      <tr>
                        <td><c:out value="${board.bno}" /></td>
                        <td><a href="/board/get?bno=<c:out value="${board.bno}"/>"><c:out value="${board.title}" /></a></td>
                        <td><c:out value="${board.writer}" /></td>
                        <%-- https://github.com/sargue/java-time-jsptags --%>
                        <td><javatime:format value="${board.regdate}" pattern="yyyy-MM-dd"></javatime:format></td>
                        <td><javatime:format value="${board.updateDate}" pattern="yyyy-MM-dd"></javatime:format></td>
                      </tr>
                    </c:forEach>
                  </tbody>
                </table>
              </div>
            </div>
          </div>

        </div>
        <!-- /.container-fluid -->

      </div>
      <!-- End of Main Content -->


      <!-- Footer -->
      <%@include file="../includes/footer.jsp"%>
      <!-- End of Footer -->

    </div>
    <!-- End of Content Wrapper -->

  </div>
  <!-- End of Page Wrapper -->
  <%@include file="../includes/dialogAndScript.jsp"%>
</body>

</html>