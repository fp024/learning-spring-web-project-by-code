<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8"/>
  <title>Ajax 파일 업로드 테스트 페이지</title>
  <style>
    .uploadResult {
      width: 100%;
      background-color: gray;
    }

    .uploadResult ul {
      display:flex;
      flex-flow: row;
      justify-content: center;
      align-items: center;
    }

    .uploadResult ul li {
      list-style:none;
      padding: 10px;
    }

    .uploadResult ul li img {
      width: 20px
    }
  </style>
</head>

<body>
<h1>Upload with Ajax</h1>

<div class="uploadDiv">
  <input type="file" name="uploadFile" multiple>
</div>

<div class="uploadResult">
  <ul>

  </ul>
</div>

<button id="uploadBtn">Upload</button>

<script>
  document.addEventListener('DOMContentLoaded', () => {
    const regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$")
    const maxSize = 1024 * 1024 * 5 // web.xml에 단일 파일 최대 크기를 20MB로 정의했었는데, 5MB로 해보자.

    const uploadResult = document.querySelector('.uploadResult ul')

    function showUploadedFile(uploadResultArr) {
      let str = ""
      for (const obj of uploadResultArr) {
        if (!obj.image) {
          str += "<li><img src='/resources/img/attach.png'>" + obj.fileName + "</li>";
        } else {
          str += "<li>" + obj.fileName + "</li>";
        }
      }
      uploadResult.insertAdjacentHTML('beforeend', str)
    }

    function checkExtension(fileName, fileSize) {
      if (fileSize > maxSize) {
        alert("파일 사이즈 초과")
        return false
      }

      if (regex.test(fileName)) {
        alert("해당 종류의 파일은 업로드할 수 없습니다.")
        return false
      }
      return true
    }

    const cloneObj = document.querySelector('.uploadDiv').cloneNode(true)

    document.querySelector('button').addEventListener('click', () => {
      const formData = new FormData()
      const inputFile = document.querySelector('input[name="uploadFile"]')
      const files = inputFile.files // input이 1개가 명백하니, querySelector를 사용했고, 이것은 배열을 반환하지 않으므로, 배열참조를 제거하자!

      console.log(files)

      // 파일 데이터를 폼 데이터에 추가
      for (const file of files) {
        if (!checkExtension(file.name, file.size)) {
          return false
        }
        formData.append("uploadFile", file)
      }

      fetch("/uploadAjaxAction", {
        method: "POST",
        body: formData
      }).then(response => response.json())
        .then(result => {
          console.log(result)

          showUploadedFile(result)

          document.querySelector('.uploadDiv').replaceWith(cloneObj)
      })
    })
  })
</script>

</body>
</html>
