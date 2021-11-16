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
          <h1 class="h3 mb-2 text-gray-800">Board Read Page</h1>


          <!-- DataTales Example -->
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">Board Read Page</h6>
            </div>
            <div id="board-read-body" class="card-body">
              <div class="form-group">
                <label>Bno</label> <input class="form-control" name="bno" value="<c:out value='${board.bno}'/>" readonly="readonly">
              </div>
              <div class="form-group">
                <label>Title</label> <input class="form-control" name="title" value="<c:out value='${board.title}'/>" readonly="readonly">
              </div>
              <div class="form-group">
                <label>Text area</label>
                <textarea class="form-control" rows="5" name="content" readonly="readonly"><c:out value='${board.content}' /></textarea>
              </div>
              <div class="form-group">
                <label>Writer</label> <input class="form-control" name="writer" readonly="readonly" value="<c:out value='${board.writer}'/>">
              </div>
              <div class="form-group">
                <label>Register Date</label> <input class="form-control" name="regDate" readonly="readonly"
                  value="<javatime:format value="${board.regdate}" pattern="yyyy-MM-dd HH:mm:ss" />">
              </div>
              <div class="form-group">
                <label>Update Date</label> <input class="form-control" name="regDate" readonly="readonly"
                  value="<javatime:format value="${board.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" />">
              </div>
              <button data-oper="modify" class="btn btn-primary">Modify</button>
              <button data-oper="list" class="btn btn-secondary">List</button>

              <form id="operForm" action="/board/modify" method="get">
                <input type="hidden" id="bno" name="bno" value="<c:out value="${board.bno}"/>">
                <input type="hidden" name="pageNum" value="<c:out value="${criteria.pageNum}"/>">
                <input type="hidden" name="searchCodes" value="<c:out value="${criteria.searchCodesWithJoined}"/>">
                <input type="hidden" name="keyword" value="<c:out value="${criteria.keyword}"/>">
              </form>
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

  <script type="text/javascript">
      $(document).ready(function() {
        var $operForm = $("#operForm");

        $("#board-read-body button[data-oper='modify']").on("click", function(e) {
          $operForm.attr("action", "/board/modify").submit();
        });

        // 빈폼으로 Submit을 해버리면 결과 URL 끝에 ?가 붙는다.
        $("#board-read-body button[data-oper='list']").on("click", function(e) {
          $operForm.find("#bno").remove();
          $operForm.attr("action", "/board/list").submit();
          $operForm.submit();
        });
      });
    </script>
</body>

</html>