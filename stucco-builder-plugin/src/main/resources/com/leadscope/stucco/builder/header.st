/**
 * Stucco ToxML API - a Java interface for parsing and creating ToxML documents
 * Copyright (C) 2011 Scott Miller - Leadscope, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
