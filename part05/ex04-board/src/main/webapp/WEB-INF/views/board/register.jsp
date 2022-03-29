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
          <h1 class="h3 mb-2 text-gray-800">Board Register</h1>


          <!-- DataTales Example -->
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">Board Register</h6>
            </div>
            <div class="card-body">
              <form role="form" action="/board/register" method="post">
                <div class="form-group">
                  <label>Title</label> <input class="form-control" name="title">
                </div>
                <div class="form-group">
                  <label>Text area</label>
                  <textarea class="form-control" rows="5" name="content"></textarea>
                </div>
                <div class="form-group">
                  <label>Writer</label> <input class="form-control" name="writer">
                </div>
                <button type="submit" class="btn btn-primary">Submit Button</button>
                <button type="reset" class="btn btn-secondary">Reset Button</button>
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

</body>

</html>