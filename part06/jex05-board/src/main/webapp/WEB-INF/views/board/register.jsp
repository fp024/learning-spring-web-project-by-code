<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time"%>
<!DOCTYPE html>
<html lang="ko">

<%@include file="../includes/header.jsp"%>
<link href="/resources/css/upload-ajax.css" rel="stylesheet">

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


        <!-- DataTales Example : 게시물 신규 폼 영역 -->
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

        <!-- 첨부파일 추가 영역 -->
        <div class="card shadow mb-4">
          <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-primary">File Attach</h6>
          </div>
          <div class="card-body">
            <div class="form-group uploadDiv">
              <input type="file" name="uploadFile" multiple>
            </div>

            <div class="uploadResult">
              <ul>

              </ul>
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


<script>
  $(document).ready(function (e) {
    var formObj = $("form[role='form']");

    $("button[type='submit']").on("click", function (e) {
      e.preventDefault();
      console.log("submit clicked");

      var str = "";

      $(".uploadResult ul li").each(function (i, obj) {
        var jobj = $(obj);
        console.dir(jobj);
        str += "<input type='hidden' name='attachList[" + i + "].fileName' value='" + jobj.data("filename") + "'>";
        str += "<input type='hidden' name='attachList[" + i + "].uuid' value='" + jobj.data("uuid") + "'>";
        str += "<input type='hidden' name='attachList[" + i + "].uploadPath' value='" + jobj.data("path") + "'>";
        str += "<input type='hidden' name='attachList[" + i + "].fileType' value='" + jobj.data("type") + "'>";
      });
      formObj.append(str).submit();

    });

    var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
    var maxSize = 5242880;

    function checkExtension(fileName, fileSize) {
      if (fileSize > maxSize) {
        alert("파일 사이즈 초과");
        return false;
      }

      if (regex.test(fileName)) {
        alert("해당 종류의 파일은 업로드할 수 없습니다.");
        return false;
      }
      return true;
    }

    function showUploadResult(uploadResultArr) {
      if (!uploadResultArr || uploadResultArr.length == 0) {
        return;
      }

      var uploadUL = $(".uploadResult ul");
      var str = "";

      $(uploadResultArr).each(function (i, obj) {
        if (obj.fileType === 'IMAGE') {
          var fileCallPath = encodeURIComponent(
              obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName);
          var originPath =
              obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName;
          console.log(originPath);
          str += "<li data-path='" + obj.uploadPath + "' data-uuid='" + obj.uuid + "' data-filename='" + obj.fileName + "' data-type='" + obj.fileType + "'><div>"
              + "<span>" + obj.fileName + "</span>"
              + "<button type='button' data-file=\'" + fileCallPath + "\' data-type='IMAGE' class='btn btn-warning btn-circle'><i class='fas fa-times'></i></button><br>"
              + "<img src='/display?fileName=" + fileCallPath + "'></a>"
              + "</div></li>";
        } else {
          var fileCallPath = encodeURIComponent(
              obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName);
          str += "<li data-path='" + obj.uploadPath + "' data-uuid='" + obj.uuid + "' data-filename='" + obj.fileName + "' data-type='" + obj.fileType + "'><div>"
              + "<span>" + obj.fileName + "</span>"
              + "<button type='button' data-file=\'" + fileCallPath + "\' data-type='NORMAL' class='btn btn-warning btn-circle'><i class='fas fa-times'></i></button><br>"
              + "<a href='/download?fileName=" + fileCallPath
              + "'><img src='/resources/img/attach.png'></a>"
              + "</div></li>";
        }
      });

      uploadUL.append(str);
    }

    // 게시글 등록 완료가 안된상태에서도 미리 ajax 파일 업로드함.
    $("input[type='file']").change(function () {
      var formData = new FormData();
      var inputFile = $("input[name='uploadFile']");
      var files = inputFile[0].files;

      for (var i = 0; i < files.length; i++) {
        if (!checkExtension(files[i].name, files[i].size)) {
          return false;
        }
        formData.append("uploadFile", files[i]);
      }

      $.ajax({
        url: '/uploadAjaxAction',
        processData: false,
        contentType: false,
        data: formData,
        type: 'POST',
        dataType: 'json',
        success: function (result) {
          console.log(result);
          showUploadResult(result); // 업로드 결과 함수.

        }
      });
    });

    $(".uploadResult").on("click", "button", function () {
      console.log("delete file");

      var targetFile = $(this).data("file");
      var type = $(this).data("type");

      var targetLi = $(this).closest("li");

      $.ajax({
        url: '/deleteFile',
        data: {fileName: targetFile, type: type},
        dataType: 'text',
        type: 'POST',
        success: function (result) {
          alert(result);
          targetLi.remove();
        }
      });
    });

  });
</script>
</body>

</html>