/**
 * Copyright 2011 Leadscope, Inc. All rights reserved.
 * LEADSCOPE PROPRIETARY and CONFIDENTIAL. Use is subject to license terms.
 */
$if (packageName)$
package com.leadscope.stucco.model.$packageName$;

$if (usingUtilPackage)$
import java.util.*;

$endif$
$if (usingModelPackage)$
import com.leadscope.stucco.model.*;

$endif$
$else$
package com.leadscope.stucco.model;

$if (usingUtilPackage)$
import java.util.*;

$endif$
$endif$
import com.leadscope.stucco.*;
