<!DOCTYPE html>
<html lang="en-US">
<head>
<title>On Sale Today</title>
<link rel="stylesheet" href="onsaletoday.css">
<link rel="stylesheet" href="bootstrap.css">
</head>
<body>
  <!-- HEADER -->
  <div class="header">
    <img src="logo_elca.png" />
    <p>Project Information Management</p>
  </div>
  <!-- LEFT MENU -->
  <div style="width: 25%; position: relative;">
    <div class="menu width50">
      <ul>
        <li><a href="#" class="activateItem">Projects list</a></li>
        <li style="line-height: 20px;">&nbsp;</li>
        <li><a href="#" class="activateItem">New</a></li>
        <li class="potentialItem">Project</li>
        <li class="regularItem">Customer</li>
        <li class="regularItem">Supplier</li>
      </ul>
    </div>
  </div>
  <!-- END OF LEFT MENU -->
  <!-- CONTENT -->
  <div class="content">
    <div class="header">New Project</div>
    <br class="clear" />
    <div class="form">
      <table class="tbl normalText">
        <tr>
          <td>Project Number <span class="mandatory">*</span></td>
          <td><input type="text" /></td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>Project name <span class="mandatory">*</span></td>
          <td colspan="3"><input type="text" style="width: 100%" /></td>
        </tr>
        <tr>
          <td>Customer <span class="mandatory">*</span></td>
          <td colspan="3"><input type="text" style="width: 100%" /></td>
        </tr>
        <tr>
          <td>Group <span class="mandatory">*</span></td>
          <td><input type="text" /></td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>Members</td>
          <td colspan="3"><input type="text" style="width: 100%" /></td>
        </tr>
        <tr>
          <td>Status <span class="mandatory">*</span></td>
          <td><input type="text" /></td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>Start date <span class="mandatory">*</span></td>
          <td><input type="text" /></td>
          <td>End date</td>
          <td><input type="text" style="float: right;" /></td>
        </tr>
      </table>

    </div>
  </div>
  <!-- END OF CONTENT -->
</body>
</html>
